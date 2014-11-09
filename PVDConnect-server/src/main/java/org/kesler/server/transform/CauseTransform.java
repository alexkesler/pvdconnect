package org.kesler.server.transform;

import org.kesler.common.CauseDTO;
import org.kesler.server.domain.Cause;
import org.kesler.server.domain.cause.Applicator;
import org.kesler.server.domain.cause.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public abstract class CauseTransform {
    private static final Logger log = LoggerFactory.getLogger(CauseTransform.class);

    public static CauseDTO transform(Cause cause) {
        log.debug("Converting Cause " + cause.getRecord().getRegnum() + " to CauseDTO...");
        CauseDTO causeDTO = new CauseDTO();

        causeDTO.setCauseId(cause.getCauseId());
        causeDTO.setRecordDTO(RecordTransform.transform(cause.getRecord()));
        causeDTO.setPurposeString(decodePurpose(cause.getPurpose()));
        causeDTO.setStateString(decodeState(cause.getState()));
        causeDTO.setStatusMdString(decodeStatusMd(cause.getStatusMd()));
        causeDTO.setEstimateDate(cause.getEstimateDate());
        causeDTO.setStateChangeDate(cause.getStateChangeDate());

        List<String> applicators = new ArrayList<String>();
        for (Applicator applicator:cause.getApplicators()) {
            applicators.add(applicator.getCommonName());
        }

        causeDTO.setApplicators(applicators);

        causeDTO.setObj(cause.getObj()==null?"Не опр":cause.getObj().getFullAddress());

        causeDTO.setCurStep(decodeStep(cause.getCurStep()));

        List<String> steps = new ArrayList<String>();
        for (Step step:cause.getSteps()) {
            steps.add(decodeStep(step));
        }

        causeDTO.setSteps(steps);

        log.debug("  purpose: " + causeDTO.getPurposeString());
        log.debug("  state: " + causeDTO.getStateString());
        log.debug("  statusMd: " + causeDTO.getStatusMdString());


        log.debug("Converting Cause " + cause.getRecord().getRegnum() + " to CauseDTO complete.");
        return causeDTO;
    }

    private static String decodeState(Integer state) {

        String stateString = "Не определено";

        // расшифровываем статус
        if (state != null) {
            switch (state) {
                case 0:
                    stateString = "В работе";
                    break;
                case 1:
                    stateString = "Исполнено";
                    break;
                case 2:
                    stateString = "Приостановлено";
                    break;
                default:
                    stateString = "Не определено(" + state + ")";
            }
        }
        log.debug("Decode state " + state + "--> " + stateString);
        return stateString;

    }

    private static String decodeStatusMd(String statusMd) {
        String statusMdString;

        switch (statusMd) {
            case "001":
                statusMdString = "Сформировано";
                break;
            case "002":
                statusMdString = "Загружено";
                break;
            case "003":
                statusMdString = "Отказано в загрузке";
                break;
            case "004":
                statusMdString = "Принято в работу";
                break;
            case "005":
                statusMdString = "Ошибка маршрутизации";
                break;
            case "006":
                statusMdString = "Приостановлено";
                break;
            case "007":
                statusMdString = "Завершено";
                break;
            case "009":
                statusMdString = "Отказано в обработке";
                break;
            case "010":
                statusMdString = "Приостановление снято";
                break;
            case "011":
                statusMdString = "Ожидание результата ГКУ";
                break;
            case "012":
                statusMdString = "Сведения отсутствуют";
                break;
            case "013":
                statusMdString = "Возврат по заявлению";
                break;
            case "014":
                statusMdString = "Возврат без рассмотрения";
                break;
            case "900":
                statusMdString = "отказано в повторной загрузке пакета";
                break;
            default:
                statusMdString = "Не определен ("+statusMd+")";
        }

        log.debug("Decode statusMD " + statusMd + "--> " + statusMdString);


        return statusMdString;
    }

    private static String decodePurpose(Integer purpose) {

        String purposeString;

        switch (purpose) {
            case 101:
                purposeString = "ОКУ: Внесение в ГКН сведений о ранее учтённом земельном участке";
                break;
            case 102:
                purposeString = "ОКУ: Государственный кадастровый учёт земельного участка";
                break;
            case 103:
                purposeString = "ОКУ: Государственный кадастровый учёт изменений земельного участка (кроме учёта изменений адреса правообладателя)";
                break;
            case 104:
                purposeString = "ОКУ: Снятие с государственного кадастрового учёта земельного участка, сведения о котором имеют временный характер";
                break;
            case 105:
                purposeString = "ОКУ: Исправление кадастровой ошибки в сведениях ГКН о земельном участке";
                break;
            case 106:
                purposeString = "ОКУ: Исправление технической ошибки в сведениях ГКН о земельном участке";
                break;
            case 131:
                purposeString = "ОКУ: Государственный кадастровый учёт изменения адреса правообладателя земельного участка";
                break;
            case 201:
                purposeString = "ОРП: Регистрация ранее возникшего вещного права";
                break;
            case 203:
                purposeString = "ОРП: Регистрация права собственности";
                break;
            case 204:
                purposeString = "ОРП: Регистрация права общей долевой собственности";
                break;
            case 205:
                purposeString = "ОРП: Регистрация права общей совместной собственности";
                break;
            case 206:
                purposeString = "ОРП: Регистрация прекращения права (без перехода права)";
                break;
            case 222:
                purposeString = "ОРП: Регистрация права постоянного (бессрочного) пользования";
                break;
            case 223:
                purposeString = "ОРП: Регистрация права хозяйственного ведения";
                break;
            case 224:
                purposeString = "ОРП: Регистрация права оперативного управления";
                break;
            case 225:
                purposeString = "ОРП: Регистрация сервитута (права ограниченного пользования чужим земельным участком)";
                break;
            case 302:
                purposeString = "ОРП: Регистрация ранее возникшего вещного права";
                break;
            case 303:
                purposeString = "ОРП: Регистрация договора участия в долевом строительстве";
                break;
            case 304:
                purposeString = "ОРП: Регистрация соглашения об изменении условий договора";
                break;
            case 305:
                purposeString = "ОРП: Регистрация соглашения об уступке требований (переводе долга) по договору";
                break;
            case 306:
                purposeString = "ОРП: Регистрация соглашения о расторжении договора";
                break;
            case 307:
                purposeString = "ОРП: Регистрация соглашения об изменении содержания закладной";
                break;
            case 311:
                purposeString = "ОРП: Регистрация договора купли-продажи";
                break;
            case 323:
                purposeString = "ОРП: Регистрация договора аренды (субаренды)";
                break;
            case 324:
                purposeString = "ОРП: Регистрация договора об ипотеке";
                break;
            case 402:
                purposeString = "ОРП: Регистрация ипотеки (возникающей на основании закона)";
                break;
            case 404:
                purposeString = "ОРП: Регистрация иного ограничения (обременения)";
                break;
            case 405:
                purposeString = "ОРП: Выдача закладной";
                break;
            case 407:
                purposeString = "ОРП: Регистрация прекращения сделки (без заключения соглашения о расторжении сделки)";
                break;
            case 408:
                purposeString = "ОРП: Регистрация прекращения ограничения (обременения)";
                break;
            case 501:
                purposeString = "ОРП: Внесение изменений в ЕГРП";
                break;
            case 502:
                purposeString = "ОРП: Исправление технической ошибки по заявлению";
                break;
            case 601:
                purposeString = "ОРП: Принятие дополнительных документов на регистрацию прав";
                break;
            case 602:
                purposeString = "ОКУ: Принятие дополнительных документов на кадастровый учёт";
                break;
            case 604:
                purposeString = "ОРП: Прекращение регистрации";
                break;
            case 606:
                purposeString = "ОРП: Повторная выдача нового свидетельства";
                break;
            case 609:
                purposeString = "ОРП: Погашение регистрационной записи об ипотеке";
                break;
            case 610:
                purposeString = "ОРП: Внесение в ЕГРП записи о невозможности государственной регистрации перехода, ограничения (обременения), прекращения права без личного участия правообладателя (его законного представителя)";
                break;
            case 701:
                purposeString = "ОКУ: Предоставление сведений о земельном участке";
                break;
            case 702:
                purposeString = "ОКУ: Предоставление сведений в виде кадастрового плана территории";
                break;
            case 704:
                purposeString = "ОКУ: Предоставление сведений ГКН в виде копии документа, на основании которого сведения об объекте недвижимости внесены в ГКН";
                break;
            case 751:
                purposeString = "ОРП: Предоставление общедоступных сведений о правах на объект недвижимого имущества";
                break;
            case 752:
                purposeString = "ОРП: Предоставление сведений о содержании правоустанавливающих документов";
                break;
            case 753:
                purposeString = "ОРП: Предоставление обобщенных сведений (выписки) о правах отдельного лица на имеющиеся и имевшиеся у него объекты недвижимости";
                break;
            case 754:
                purposeString = "ОРП: Предоставление выписки о переходе прав на объект недвижимости";
                break;
            case 756:
                purposeString = "ОРП: Предоставление информации (справки) о лицах, получивших сведения об объекте недвижимого имущества";
                break;
            case 757:
                purposeString = "ОРП: Выдача копии договоров и иных документов, выражающих содержание односторонних сделок, совершенных в простой письменной форме";
                break;
            case 801:
                purposeString = "ОКУ: Государственный кадастровый учёт здания, сооружения, помещения, объекта незавершённого строительства";
                break;
            case 802:
                purposeString = "ОКУ: Внесение в ГКН сведений о ранее учтённом здании, сооружении, помещении, объекте незавершённого строительства";
                break;
            case 803:
                purposeString = "ОКУ: Государственный кадастровый учёт изменений здания, сооружения, помещения, объекта незавершённого строительства (кроме учёта изменений адреса правообладателя)";
                break;
            case 804:
                purposeString = "ОКУ: Исправление технической ошибки в сведениях ГКН о здании, сооружении, помещении или об объекте незавершённого строительства";
                break;
            case 901:
                purposeString = "ОКУ: Предоставление сведений о здании, сооружении, помещении, объекте незавершённого строительства";
                break;
            case 902:
                purposeString = "ОКУ: Предоставление копии документа, на основании которого сведения о здании, сооружении, помещении, объекте незавершённого строительства внесены в ГКН";
                break;
            case 1001:
                purposeString = "ОКУ: Снятие с государственного кадастрового учёта здания, сооружения, помещения или объекта незавершённого строительства в связи с прекращением его существования";
                break;
            case 1003:
                purposeString = "ОКУ: Исправление кадастровой ошибки в сведениях ГКН о здании, сооружении, помещении или об объекте незавершённого строительства";
                break;
            case 1005:
                purposeString = "ОКУ: Снятие с государственного кадастрового учёта помещения в связи с регистрацией права собственности на здание или сооружение";
                break;
            default:
                purposeString = "Не опеределен";

        }

        log.debug("Decode purpose " + purpose + " --> " + purposeString);

        return purposeString;
    }

    private static String decodeStep(Step step) {
        String stepString;

        Date dateBegin = step.getDateBegin();
        Date dateEnd = step.getDateEnd();
        Date estimateDate = step.getEstimateDate();
        String operationString = decodeStepOperation(step.getOperation());
        String resolution = step.getResolution();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String dateBeginString = dateBegin==null?"не опр":dateFormat.format(dateBegin);
        String esimateDateString = estimateDate==null?"не опр":"/"+dateFormat.format(estimateDate)+"/";
        String dateEndString = dateEnd==null?esimateDateString:dateFormat.format(dateEnd);

        stepString = dateBeginString +
                "-"+
                dateEndString +
                " [" + operationString + "]" +
                (resolution==null?"":" -> " + resolution);


        return stepString;
    }

    private static String decodeStepOperation(String operation) {
        String operationString;

        switch (operation) {
            case "PackageCorrection":
                operationString="Корректировка обращения";
                break;
            case "DocumentImageAdding":
                operationString="Присоединение образов";
                break;
            case "MDDataWaiting":
                operationString="Ожидание сведений из МД";
                break;
            case "CauseForming":
                operationString="Формирование дела по обращению";
                break;
            case "IssuedDocumentPrinting":
                operationString="Печать выдаваемых документов";
                break;
            case "PaymentWaiting":
                operationString="Ожидание оплаты";
                break;
            case "DataCorrection":
                operationString="Корректировка сведений";
                break;
            case "DocumentIssue":
                operationString="Выдача документов";
                break;
            default:
                operationString="Не определено ("+operation+")";
        }

        return operationString;
    }


}

package org.kesler.client.service;


import org.kesler.client.domain.Check;
import org.kesler.common.ProgressStatus;

import java.util.Collection;

public interface CheckService {
    public Collection<Check> getAllChecks();
    public void doChecks();
    public ProgressStatus getProcessStatus();
}

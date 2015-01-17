package org.kesler.client.gui.branch;

import org.kesler.client.domain.Branch;

import java.util.Comparator;

/**
 * Created by alex on 17.01.15.
 */
public class BranchComparator implements Comparator<Branch> {
    @Override
    public int compare(Branch o1, Branch o2) {
        Integer i1 = Integer.parseInt(o1.getCode());
        Integer i2 = Integer.parseInt(o2.getCode());
        return Integer.compare(i1,i2);
    }
}

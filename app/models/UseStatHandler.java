package models;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import models.*;

public class UseStatHandler {

    public static ArrayList<Function> getMyMru(String userid) {
        ArrayList<Function> mymru = new ArrayList<Function>();
        List<UseStat> myUsestat = UseStat.find("userid = ? order by id desc", userid).fetch(10);

        for ( Iterator<UseStat> it = myUsestat.iterator(); it.hasNext(); ) {
            Long functionId = it.next().function;
            Function f = Function.findById(functionId);
            mymru.add(f);
        }
        return mymru;
    }

} 
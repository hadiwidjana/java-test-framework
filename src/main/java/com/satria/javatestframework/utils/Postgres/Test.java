package com.satria.javatestframework.utils.Postgres;

import static com.satria.javatestframework.utils.Postgres.DbFunction.DBconnection;
import static com.satria.javatestframework.utils.Postgres.DbFunction.getRandomDphId;

public class Test {

    public static void main (String args[]){
//        db.read_data(conn,"trade_confirmations");
        System.out.println(getRandomDphId(DBconnection("postgres","tender_dev","gokomodo")));
//        UpdateTcDate(DBconnection("postgres","tender_dev","gokomodo"),"trade_confirmations","zlXoOKAY",-30,-30,-30);
    }
}

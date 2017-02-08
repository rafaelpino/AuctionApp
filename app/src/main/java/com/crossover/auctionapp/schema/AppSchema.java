package com.crossover.auctionapp.schema;

import android.content.ContentValues;
import android.provider.BaseColumns;

/**
 * Created by rafaelpino on 12/13/16.
 */
public class AppSchema {


    public static abstract class UserSchema implements BaseColumns {
        public static final String TABLE_NAME ="Users";
        public static final String ID = "id";
        public static final String USR_LOGIN = "login";
        public static final String USR_NAMES = "name";
        public static final String USR_LAST_NAMES = "lastName";
        public static final String USR_AGE = "age";
        public static final String USR_GENDER = "gender";
        public static final String USR_DOCID = "documentId";
        public static final String USR_PASSWORD = "password";
    }

    public static abstract class ItemSchema implements BaseColumns {
        public static final String TABLE_NAME ="Items";
        public static final String ID = "id";
        public static final String ITM_NAME = "name";
        public static final String ITM_PRICE = "price";
        public static final String ITM_BID_INCREMENT = "bidIncrement";
        public static final String ITM_DURATION = "offerDuration";
        public static final String ITM_LAST_BID_USER = "lastBidUser";
        public static final String ITM_IS_CLOSED = "isClosed";
    }


}

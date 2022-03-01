package com.huawei.storage.oceanstor.rest.constants;

public class OperationConstants {

	public static class HttpErrorConstant
    {
        /**
         * rest
         */
        public static final int OK = 0;

        /**
         * authentication error
         */
        public static final int ERROR_401 = -401;
    }
	
	public static class RestRequestConstant {
		
		public static final String REST_LOGIN_URI = "/xxxx/sessions";
		public static final String REST_LOGOUT_URI = "/sessions";
	}

}

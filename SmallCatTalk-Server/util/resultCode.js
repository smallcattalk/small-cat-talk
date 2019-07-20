
let resultCode = {};



//=======================================
//           LOGIN RESULT CODE
//========================================
/** ======================================
              @POST /login
 =========================================*/
resultCode.LOGIN = {};
resultCode.LOGIN.DB_ERROR = -2;
resultCode.LOGIN.PASSWORD_ERROR = -1;
resultCode.LOGIN.USER_NOT_FOUND = 0;
resultCode.LOGIN.SUCCESS = 1;

//========================================
//   EXIST USER CONFIRM RESULT CODE
//========================================
/** =================================
           @GET /check/:id
 ===================================*/
resultCode.ID_CHECK = {};
resultCode.ID_CHECK.IMPOSSIBLE = 0;
resultCode.ID_CHECK.POSSIBLE = 1;


//=======================================
//           JOIN RESULT CODE
//=======================================
/** =================================
              @POST /join
 ===================================*/
resultCode.JOIN= {};
resultCode.JOIN.FAILURE = 0;
resultCode.JOIN.SUCCESS = 1;


//=====================================
//       SEARCH RESULT CODE
//=====================================
/** =================================
         @GET /search/user/:id
 ===================================*/
resultCode.SEARCH_USER={};
resultCode.SEARCH_USER.USER_NOT_FOUND = 0;
resultCode.SEARCH_USER.SUCCESS = 1;
/** =================================
         @GET /search/room/:id
 ===================================*/
resultCode.SEARCH_ROOM={};
resultCode.SEARCH_ROOM.ROOM_NOT_FOUND = 0;
resultCode.SEARCH_ROOM.SUCCESS = 1;


//=====================================
//         FRIEND RESULT CODE
//=====================================
resultCode.FREIND={};

/** =========== ADD =============
        @POST /friend
 * =============================**/

resultCode.FREIND.ADD={};
resultCode.FREIND.ADD.DB_ERROR = -1;
resultCode.FREIND.ADD.FAILURE = 0;
resultCode.FREIND.ADD.SUCCESS = 1;

/* =========== DELETE ===========
        @DELETE /friend
* ================================*/
resultCode.FREIND.DELETE={};
resultCode.FREIND.DELETE.FAILURE = 0;
resultCode.FREIND.DELETE.SUCCESS = 1;


/** =========== load =============
         @POST /friend/list
 * =============================**/
resultCode.FREIND.LOAD={};
resultCode.FREIND.LOAD.DB_ERROR = -1;
resultCode.FREIND.LOAD.FAILURE = 0;
resultCode.FREIND.LOAD.SUCCESS = 1;


//===================================
//          ROOM RESULT CODE
//===================================
resultCode.ROOM = {};

/**==================================
 @POST /room
 ===================================*/
resultCode.ROOM.ADD ={};
resultCode.ROOM.ADD.FAILURE = 0;
resultCode.ROOM.ADD.SUCCESS = 1;
resultCode.ROOM.ADD.EXIST = 2;




//===================================
//          ROOM RESULT CODE
//===================================

/**==================================
        @POST /room/list
 ===================================*/
resultCode.ROOM.LIST ={};
resultCode.ROOM.LIST.FAILURE = 0;
resultCode.ROOM.LIST.SUCCESS = 1;




//===================================
//        UPDATE USER
//===================================
resultCode.UPDATE = {};

/**==================================
        @POST /update/user
 ===================================*/
resultCode.UPDATE.USER ={};
resultCode.UPDATE.USER.BAD_REQUEST = -1;
resultCode.UPDATE.USER.FAILURE = 0;
resultCode.UPDATE.USER.SUCCESS =1;








//===================================
//           CHAT
//===================================
resultCode.CHAT = {};

/**==================================
 @POST  /chat/new
 ===================================*/
resultCode.CHAT.CREATE ={};
resultCode.CHAT.CREATE.FAILURE = 0 ;
resultCode.CHAT.CREATE.SUCCESS = 1 ;
/**==================================
    @POST      /chat/list
 ===================================*/
resultCode.CHAT.LOAD ={};
resultCode.CHAT.LOAD.FAILURE = 0 ;
resultCode.CHAT.LOAD.SUCCESS = 1 ;


module.exports = resultCode;
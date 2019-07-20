const should = require('should');
const test = require('supertest');
const assert = require('assert');
const app = require('../app');
const resultCode = require('../util/resultCode');

const ID_CHECK_TEST = require('./TestUnit/check.spec.js');
const LOGIN_TEST = require('./TestUnit/login.spec');
const FRIEND_CHECK_TEST = require('./TestUnit/friend.spec');
const SEARCH_TEST = require('./TestUnit/search.spec');
const ROOM_TEST = require('./TestUnit/room.spec');
const UPDATE_USER_TEST = require('./TestUnit/update.spec');
const ROOM_LIST_TEST = require('./TestUnit/roomList.spec');
const JOIN_TEST = require('./TestUnit/join.spec');

const integration_test = async () => {

    //  await ID_CHECK_TEST();
    //  await  LOGIN_TEST();
    //  await  FRIEND_CHECK_TEST();
    //  await SEARCH_TEST();
    //  await ROOM_TEST();

   // await UPDATE_USER_TEST();
    JOIN_TEST();
    //await ROOM_LIST_TEST();
    //  await process.exit();
};

integration_test();


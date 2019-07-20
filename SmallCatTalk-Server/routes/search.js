const express =require('express');

const router = express.Router();

const User = require('../schemas/user');
const Room = require('../schemas/room');
const resultCode = require('../util/resultCode');


router.get('/user/:id',async (req,res)=>{
    const searchId = req.params.id.toLowerCase();
    const users = await User.find({userId:{ $regex: '.*' + searchId + '.*' } },
        function(err,data){
            console.log('data',data);
        });
    for( user of users) {
        delete user.userPassword;
        delete user.friendsList;
        delete user.roomsList;
    }
    if(users.length > 0) {
        res.json({result:resultCode.SEARCH_USER.SUCCESS,users});
    }else{
        res.status(404);
        res.json({result:resultCode.SEARCH_USER.USER_NOT_FOUND})
    }
});

router.get('/room/:id',async (req,res)=>{

    const _id = req.params.id;
    const room = await Room.findOne({_id});

    if(room) {
        res.json({result:resultCode.SEARCH_ROOM.SUCCESS,room});
    }else{
        res.status(404);
        res.json({result:resultCode.SEARCH_ROOM.ROOM_NOT_FOUND})
    }
});

module.exports = router;
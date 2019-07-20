const express = require('express');
const router = express.Router();
const Room = require('../schemas/room');
const User = require('../schemas/user');
const mongoose = require('mongoose');
const { Schema }  = mongoose;
const {Types:{ObjectId}} = Schema;
const resultCode =  require('../util/resultCode');

router.post('/',async (req,res)=>{
    const { usersList } = req.body;
    const newGroupRoom = new Room({});
    let users = [];
    for (user_Id of usersList){
        let castingId = mongoose.Types.ObjectId(user_Id.toString());
        let partyUser = await User.findOne({_id:castingId});
        users.push(partyUser)
    }

    await newGroupRoom.save(async (err,room)=>{
        usersList.map((userId)=>{
            const castedUserId = mongoose.Types.ObjectId(userId.toString());
            room.usersList.push(castedUserId);
        });
        await room.save(async (err,room)=>{
            users.map(async (newUser)=>{
                newUser.roomsList.push({roomId:room._id,friendId:"000000000000000000000000"});
                await newUser.save();
            });
            req.app.get('io').of('/room').emit('newRoom');
            res.status(201);
            res.json({result:resultCode.ROOM.ADD.SUCCESS,newRoom:room})
        })
    });

});


module.exports = router;
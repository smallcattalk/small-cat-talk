const express = require('express');

const router = express.Router();

const Room = require('../schemas/room');
const User = require('../schemas/user');


const mongoose = require('mongoose');
const { Schema }  = mongoose;
const {Types:{ObjectId}} = Schema;



const resultCode =  require('../util/resultCode');


router.post('/list',async (req,res)=>{

    const { _id } = req.body;

    const castedId = mongoose.Types.ObjectId(_id.toString());
    const user = await User.findOne({_id:castedId});

    let userRoomsList = user.roomsList;
    let roomsList = [];
    let usersLists = [];

    // 유저목록에서 방정보
    for ( room of userRoomsList) {

        const roomObject = await Room.findOne({_id:room.roomId});
        let userslist = roomObject.usersList;
        let usersList = [];
        // 방정보의 유저목록에서
        for (userOb of userslist){
            const userObject = await User.findOne({_id:userOb._id});
            usersList.push(userObject)
        }
        usersLists.push(usersList);
        roomsList.push(roomObject);
    }
       if (roomsList.length < 1) {
           res.json({result: resultCode.ROOM.LIST.FAILURE});
       } else {
           res.json({result: resultCode.ROOM.LIST.SUCCESS, roomsList, usersLists});
       }
});


router.post('/',async (req,res)=>{
    const { roomId , userId , friendId  } = req.body;
    const _id = mongoose.Types.ObjectId(roomId == ''?'000000000000000000000000':roomId.toString());
    const existingRoom = await Room.findOne({_id});

    if(existingRoom){
        console.log(existingRoom);
        res.status(200);
        res.json({
            result:resultCode.ROOM.ADD.FAILURE,
            existingRoom
        })
    }
    else {
        let usersList = [];
        const castedUserId = mongoose.Types.ObjectId(userId.toString());
        const castedFriendId = mongoose.Types.ObjectId(friendId.toString());

        usersList.push(
            castedUserId);
        usersList.push(
            castedFriendId
        );


        const newRoom = new Room({
        });

        await newRoom.save( async (err,room)=>{
          var newroomId =  await room._id;



            let currentUser = await User.findOne({_id:castedUserId});
            let friendUser = await  User.findOne({_id:castedFriendId});
            currentUser.roomsList.push({roomId:newroomId, friendId:castedFriendId});
            friendUser.roomsList.push({roomId:newroomId, friendId:castedUserId});

            room.usersList.push(castedUserId);
            room.usersList.push(castedFriendId);

            await currentUser.save();
            await friendUser.save();

            await room.save(async (err,room1)=>{
                res.status(201);
                req.app.get('io').of('/room').emit('newRoom');
                console.log('방생성 성공');
                //console.log(room);
                res.json({
                    result:resultCode.ROOM.ADD.SUCCESS,
                    newRoom:room1
                })
            });
        });
    }

router.get('/:id',async (req,res)=>{

    const _id = mongoose.Types.ObjectId(req.params.id.toString());

    const room =  await Room.findOne({_id});

    res.json({
        room
    })
    })
});

module.exports = router;
const express = require('express');

const router = express.Router();

const User = require('../schemas/user');

const resultCode = require('../util/resultCode');


const mongoose = require('mongoose');


router.post('/',async (req,res)=>{
    try {
        const {_id,friendId} = req.body;
        const requester = await User.findOne({_id});
        const castedFriendId = mongoose.Types.ObjectId(friendId.toString());
        let existFriend = requester.friendsList.filter((friend)=>{
            if(friend._id == friendId) {
                return true
            }
        });
        if(existFriend.length == 1){
            res.status(200);
            res.json({result:resultCode.FREIND.ADD.FAILURE})
        }
       else {
            requester.friendsList.push(friendId);
            await requester.save();
            res.status(201);
            res.json({result: resultCode.FREIND.ADD.SUCCESS, newFriend:friendId})
        }
    }catch(e){
        console.error(e);
        res.json({result:resultCode.FREIND.ADD.DB_ERROR})
    }

});

router.delete('/',async (req,res)=>{
    try {
        const {_id, friendId} = req.body;
        const requester = await User.findOne({_id});
        requester.friendsList.delete(friendId);
        await requester.save();
        res.json({result:resultCode.FREIND.DELETE.SUCCESS})
    }catch(e){
        console.error(e);
        res.json({result:resultCode.FREIND.DELETE.FAILURE})
    }

});

router.post('/list',async (req,res)=>{
    const { _id } = req.body;
    const { friendsList  } = await  User.findOne({_id});

    if(friendsList.length > 0){
        let friendsArray = [];

        for ( friend of friendsList ){

            const { _id } = friend;
            console.log(_id);
            const loadFriend = await User.findOne({ _id});
            friendsArray.push(loadFriend)
        }
        friendsArray.sort((a,b)=>{
           return  a.userName > b.userName ? 1: -1;
        });
        res.json({result:resultCode.FREIND.LOAD.SUCCESS , friendsList:friendsArray})
    }
    else{
        res.json({result:resultCode.FREIND.LOAD.FAILURE});
    }
});




module.exports = router;
const express = require('express');

const mongoose = require('mongoose');

const resultCode = require('../util/resultCode');

const router = express.Router();

const User = require('../schemas/user');

router.post('/user',async (req,res)=>{
    const { _id } = req.body;

    if(_id.length <24){
        res.status(400);
        res.json({result:resultCode.UPDATE.USER.BAD_REQUEST});
    }

    const castedId = mongoose.Types.ObjectId(_id.toString());
    const user = await  User.findOne({_id : castedId});

    if(user){
        res.json({result:resultCode.UPDATE.USER.SUCCESS , user});
    }else{
        res.status(404);
        res.json({result:resultCode.UPDATE.USER.FAILURE});
    }

});


module.exports = router;
const express = require('express');

const router = express.Router();

const User = require('../schemas/user');

const resultCode = require('../util/resultCode');

const bcrypt = require('bcrypt');

router.post('/',async (req,res)=>{
    const { userId , userName,userPassword, profileImgUrl } = req.body;
    const user = await User.findOne({userId:userId.toLowerCase()});
    if(user){

        res.json({
            result:resultCode.JOIN.FAILURE
        })
    }else{
        console.time("암호화");
        const hashPw = await bcrypt.hash(userPassword,5);
        console.timeEnd("암호화");
        var newUser;
        console.log(profileImgUrl);
        if(profileImgUrl === undefined ){
            newUser = new User({
                userId: userId.toLowerCase(), userName, userPassword: hashPw
            });
        }
        else {
            newUser = new User({
                userId: userId.toLowerCase(), userName, userPassword: hashPw, profileImgUrl
            });
        }
       await newUser.save();
        res.status(201);
        res.json({
            result:resultCode.JOIN.SUCCESS
        })
    }
});

module.exports = router;
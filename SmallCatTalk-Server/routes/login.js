const express = require('express');
const router = express.Router();
const User = require('../schemas/user');
const bcrypt = require('bcrypt');
const resultCode = require('../util/resultCode');

router.post('/', async (req,res)=>{
    let { userId,userPassword } = req.body;
    try {
        const user = await User.findOne({userId:userId.toLowerCase()});
        if ( !user ) {
            res.status(404);
            res.json({
                result: resultCode.LOGIN.USER_NOT_FOUND
            })
        } else {
            const pwCompare = await bcrypt.compare(userPassword, user.userPassword);
            delete user.userPassword;
            if (pwCompare) {
                res.json({
                    result: resultCode.LOGIN.SUCCESS,
                    user
                })
            } else {
                res.json({
                    result: resultCode.LOGIN.PASSWORD_ERROR
                })
            }
        }

    }catch (e) {
        console.error(e);
        res.json({
            result:resultCode.LOGIN.DB_ERROR
        });

    }
});

module.exports = router;
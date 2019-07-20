const express = require('express');
const router = express.Router();
const resultCode = require('../util/resultCode');
const User = require('../schemas/user');

router.get('/:id',async (req,res)=>{

    const userId  = req.params.id.toLowerCase();

    const user = await User.findOne({userId});
console.log(req.headers);
    if(user){
        res.json({
            result:resultCode.ID_CHECK.IMPOSSIBLE
        })
    }else{
        res.json({
            result:resultCode.ID_CHECK.POSSIBLE
        })
    }

});

module.exports = router;
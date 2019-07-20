const express = require('express');

const router = express.Router();

router.get('/',(req,res)=>{
    res.send('실행');
});

module.exports = router;
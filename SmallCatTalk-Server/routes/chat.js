const express = require('express');
const router = express.Router();
const Chat = require('../schemas/chat');
const resultCode = require('../util/resultCode');
const axios = require('axios');
const path = require('path');
const multer = require('multer');
const mongoose = require('mongoose');

const imgUploader = multer({
    storage : multer.diskStorage({
        destination(req,file,cb){
            cb(null ,'ImgUploads')
        },
        filename(req,file,cb){
            const ext = path.extname(file.originalname);

            cb(null,path.basename(file.originalname,ext)
                +new Date().valueOf()
                + ext
            )
        },
    }) ,
    limit:{filesize:5 * 1024 * 1024 }
});


router.get('/list/:roomId',async (req,res)=>{
    const roomId = req.params.roomId;
    const room = mongoose.Types.ObjectId(roomId.toString());
    const chats = await Chat.find({room}).populate('room').populate('user').sort('createAt');

    if(chats.length > 0){
        res.json({result:resultCode.CHAT.LOAD.SUCCESS,chats})
    }else{
        res.status(404);
        res.json({result:resultCode.CHAT.LOAD.FAILURE})
    }
});

router.post('/call',(req,res)=>{
    const {roomId,userId} = req.body;
    req.app.get('io').of('/room').to(roomId).emit('call',{userId});
    res.status(201);
    res.json({"result":1});
});

router.post('/img',imgUploader.single('img'),async (req,res)=>{
    const { user, room } = req.body;
    const imgUrl = req.file.path;
    try {
        const chatObject = new Chat({
            user,
            room,
            type:'img',
            imgUrl
        });
        await chatObject.save();

        req.app.get('io').of('/chat').to(room).emit('newChat',(chatObject));
        res.status(201);
        res.json({result: resultCode.CHAT.CREATE.SUCCESS})
    }catch (e) {
        res.status(500);
        res.json({result: resultCode.CHAT.CREATE.FAILURE})
    }
});

router.post('/newChat',async (req,res)=>{
    const {chat , user , room } = req.body;
    try {
       const chatObject = new Chat({
           user,
           room,
           chat
       });
       await chatObject.save();
       req.app.get('io').of('/chat').to(room).emit('newChat',(chatObject));
       res.status(201);
       res.json({result: resultCode.CHAT.CREATE.SUCCESS})
   }catch (e) {
       res.status(500);
       res.json({result: resultCode.CHAT.CREATE.FAILURE})
   }
});

module.exports = router;
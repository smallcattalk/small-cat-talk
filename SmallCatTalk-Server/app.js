/**=========================
//         MODULE
//=========================*/
const logger = require('morgan');
const express = require('express');
const path = require('path');
const death = require('death')({debug:true});
const connect = require('./schemas');
const webSocket = require('./sockets');
//=========================
//         ROUTER
//=========================
const indexRouter = require('./routes');
const checkIdRouter = require('./routes/check');
const joinRouter = require('./routes/join');
const loginRouter = require('./routes/login');
const searchRouter = require('./routes/search');
const friendRouter = require('./routes/friend');
const roomRouter = require('./routes/room');
const updateRouter = require('./routes/update');
const chatRouter  = require('./routes/chat');
const groupRoomRouter = require('./routes/groupRoom');

require('dotenv').config();

const app = express();
const port = process.env.PORT||9999;
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({extended:false}));
connect();

app.use('/profileImgs',express.static(path.join(__dirname,'profileImgs')));
app.use('/ImgUploads',express.static(path.join(__dirname,'ImgUploads')));
app.use('/',indexRouter);
app.use('/join',joinRouter);
app.use('/login',loginRouter);
app.use('/check',checkIdRouter);
app.use('/friend',friendRouter);
app.use('/search',searchRouter);
app.use('/update',updateRouter);
app.use('/chat',chatRouter);
app.use('/room',roomRouter);
app.use('/groupRoom',groupRoomRouter);

const server = app.listen(port,()=>{
    console.time('서버 시간');
    console.log(port,'포트에서 대기');
});
webSocket(server,app);
module.exports = app;

const socketIO = require('socket.io');
const axios = require('axios');

module.exports = (server,app)=>{
    const io = socketIO(server);

    const room = io.of('/room');
    const chat = io.of('/chat');

    app.set('io',io);

    io.on('connection', (socket) => {
        const clientId = socket.id;
        socket.on('disconnect',()=>{})
    });

    room.on('connection',(socket)=>{
        let  roomListId;
        socket.on('init',(initdata)=>{
                roomListId = initdata._id;
                socket.join(roomListId)
        });
        socket.on('disconnect',()=>{
            socket.leave(roomListId)
        });

    });

    chat.on('connection',(socket)=>{
        let currentRoomId;

        socket.on('init',(initdata)=>{
            currentRoomId = initdata.roomId;;
            socket.join(currentRoomId)
        });

        socket.on('newChat',(data)=>{
            axios.post('http://localhost:9999/chat/newChat',data);
        });

        socket.on('call',()=>{
            socket.to(chat).emit('call');
        });

        socket.on('disconnect',()=>{
            console.log(`채팅 네임스페이스 해제 ${currentRoomId}`);
            socket.leave(currentRoomId);
        })

    });


};
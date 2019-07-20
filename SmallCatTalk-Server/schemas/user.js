const mongoose = require('mongoose');

const { Schema } = mongoose;

const { Types:{ ObjectId }} = Schema;

const userSchema = new Schema({

    userId:{
        type:String,
        required:true,
        unique:true

    },
    userName:{
        type:String,
        required: true
    },
    userPassword:{
        type:String,
        required: true
    },
    profileImgUrl:{
        type:String,
        required: false,
        default:"profileImgs/default.png"

    },
    friendsList: [
        new mongoose.Schema(
            {
                friend: {type:ObjectId,ref:'User' }
            })]
    ,

    roomsList: [
        new mongoose.Schema({
            roomId: {type: ObjectId, required: true},
            friendId: {type: ObjectId, required: true}
        })]
    ,
    comment:{
        type:String,
        required: false,
        default:"상태메세지를 입력해주세요."

    }


});

module.exports = mongoose.model('User',userSchema);
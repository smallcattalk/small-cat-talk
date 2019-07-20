const mongoose = require('mongoose');
const { Schema } = mongoose;
const {Types:{ObjectId}} = Schema;


const roomSchema = new Schema({


    lastChat:{
        type:ObjectId,
        required:false,
        ref:'Chat'
    },

    unreadCount:{
        type:Number,
        required:false,
        default:0
    },
    usersList:  [new mongoose.Schema(
        {
            user: {type:ObjectId,ref:'User'}
        })],

    createAt:{
        type:Date,
        required:false,
        default: Date.now()
    }

});

module.exports = mongoose.model('Room',roomSchema);
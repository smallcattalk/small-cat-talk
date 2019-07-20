const mongoose = require('mongoose');

const { Schema } = mongoose ;

const {Types:{ObjectId}} = Schema;

const chatSchema = new Schema({
    user:{
        type:ObjectId,
        required:true,
        ref:'User'
    },
    room:{
        type:ObjectId,
        required:true,
        ref:'Room'
    },
    chat:{
      type:String,
      required:false,
        default: ''
    },
    unreadCount:{
        type:Number,
        default: 1
    },
    createAt: {
        type: Date,
        default: Date.now()
    },
    type:{
        type:String,
        required: false,
        default:'text'
    },
    imgUrl:{
        type:String,
        required: false,
        default:''
    },

});

module.exports = mongoose.model('Chat',chatSchema);
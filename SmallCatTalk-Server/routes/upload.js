const express = require('express');
const router = express.Router();
const multer = require('multer');

const imgUploader  = multer({dest:'../ImgUploads'});

router.post('/upload', imgUploader.single('img'),(req,res)=>{


});



module.exports = router;

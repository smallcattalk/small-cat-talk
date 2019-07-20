const should = require('should');
const test = require('supertest');
const assert = require('assert');
const app = require('../../app');
const resultCode = require('../../util/resultCode');

const ROOM_TEST= () => {
    describe('@POST /room  방을 생성합니다.', () => {

        it(' 방 아이디가 존재하지 않으면  새로운 방 생성 result :  1 을 반환 합니다 ', (done) => {
            test(app)
                .post('/room')
                .send(
                    {
                        roomId : '',
                        userId : '5c9ca29d8d36c4580aeb452a',
                        friendId: '5c9ca2aa8d36c4580aeb452b',
                    })
                .expect(201)
                .expect('Content-Type', /json/)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.ROOM.ADD.SUCCESS);
                    should(res.body).have.property('createdRoom');
                    done();
                })

        });


     /*   it(' 이미 방이 존재할 시 방을 가져옵니다. result : 2  ', (done) => {
            test(app)
                .post('/room')
                .send({roomId:`${createdRoomId}`,
                        userId : '5c9c85ca940b1a1db9d50f14',
                        friendId: '5c9c85d9940b1a1db9d50f15' ,
                       })
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.ROOM.ADD.EXIST);
                    should(res.body).have.property('existingRoom');
                    const  {existingRoom} = res.body;

                    console.log(existingRoom);

                    done();
                })


        });
        */
    });
};
module.exports = ROOM_TEST;

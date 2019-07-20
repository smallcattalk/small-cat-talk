const test = require('supertest');
const should = require('should');
const app = require('../../app');


const ROOMS_LIST_TEST = () =>{
    describe('@POST /room/list 방 목록 가져오기 테스트',()=>{
        it('방목록을 성공적으로 가져옵니다.',(done)=>{
            test(app)
                .post('/room/list')
                .expect(200)
                .send({"_id":"5c9dc8a33d210ca6c2f651ca"})
                .end((err,res)=>{
                    should(res.body).have.property('roomsList');
                    should(res.body).have.property('friendsList');
                    done();
                })
        })
    })
};

module.exports = ROOMS_LIST_TEST;
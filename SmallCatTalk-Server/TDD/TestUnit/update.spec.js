const test = require('supertest');
const resultCode = require('../../util/resultCode');
const should = require('should');
const app = require('../../app');

const UPDATE_USER_TEST = () => {
    describe('@POST /update/user 수정된 유저정보 로드 테스트입니다 ', () => {

        it('성공시 유저정보와 result:1 을 반환합니다',(done)=>{
           test(app)
               .post('/update/user')
               .send({_id:"5c9cf73fe3317f0fcaba9f7f"})
               .expect(200)
               .end((err,res)=>{
                   should(res.body).have.property('result',1);
                   should(res.body).have.property('user');
                   done();
               })
        });

        it(' 실패시  result:0 을 반환합니다',(done)=>{
            test(app)
                .post('/update/user')
                .send({_id:"000000000000000000000000"})
                .expect(404)
                .end((err,res)=>{
                    should(res.body).have.property('result',0);
                    done();
                })
        });

        it(' 요청사항 불일치시  result:-1 을 반환합니다',(done)=>{
            test(app)
                .post('/update/user')
                .send({_id:""})
                .expect(400)
                .end((err,res)=>{
                    should(res.body).have.property('result',-1);
                    done();
                })
        });
    });
};

module.exports = UPDATE_USER_TEST;


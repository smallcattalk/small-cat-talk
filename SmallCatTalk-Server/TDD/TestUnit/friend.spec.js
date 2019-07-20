const should = require('should');
const test = require('supertest');
const assert = require('assert');
const app = require('../../app');
const resultCode = require('../../util/resultCode');

const FRIEND_CHECK_TEST= () => {
    describe('@POST /friend/list/  친구 목록을 가져옵니다.', () => {

        it(' 친구 목록 존재시  result :1 / friendsList 배열형태로  반환합니다', (done) => {
            test(app)
                .post('/friend/list')
                .send({_id : '5c9ab72f3df40237cf3993a4'})
                .expect(200)
                .expect('Content-Type', /json/)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.FREIND.LOAD.SUCCESS);
                    done();
                })
        });


        it('  친구목록이 없을 시 result : 0  을 반환합니다', (done) => {
            test(app)
                .post('/friend/list')
                .send({_id:'5c9c85ca940b1a1db9d50f14'})
                .expect(404)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.FREIND.LOAD.FAILURE);
                    done();
                })
        });
    });
};
module.exports = FRIEND_CHECK_TEST;

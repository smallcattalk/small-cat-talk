const should = require('should');
const test = require('supertest');
const assert = require('assert');
const app = require('../../app');
const resultCode = require('../../util/resultCode');

const ID_CHECK_TEST = () => {
    describe('@GET /check 아이디 중복체크 테스트입니다.', () => {

        it(' 중복시 RESULT : 0  반환합니다', (done) => {
            test(app)
                .get('/check/TEST1')
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.ID_CHECK.IMPOSSIBLE);
                    done();
                })

        });

        it(' 아이디 가능시 RESULT : 1  반환합니다', (done) => {
            test(app)
                .get('/check/ABCD')
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.ID_CHECK.POSSIBLE);
                    done();
                })
        });
    });
};
module.exports = ID_CHECK_TEST;
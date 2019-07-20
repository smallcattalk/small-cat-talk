const should = require('should');
const test = require('supertest');
const assert = require('assert');
const app = require('../../app');
const resultCode = require('../../util/resultCode');

const LOGIN_TEST = () => {
    describe('@POST /login    로그인 테스트입니다.', () => {

        it('로그인 성공시 result:1 / user 객체를 반환합니다. ', (done) => {
            test(app)
                .post('/login')
                .send({userId:'TEST1',userPassword:'1111'})
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.LOGIN.SUCCESS);
                    should(res.body).have.property('user');
                    done();
                })

        });

        it(' 등록되지 않은 아이디로 로그인 시  result: 0  반환합니다. ', (done) => {
            test(app)
                .post('/login')
                .send({userId:'NO',userPassword:'2222'})
                .expect(404)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.LOGIN.USER_NOT_FOUND);
                    done();
                })
        });

        it(' 패스워드 불일치시  result:-1  반환합니다. ', (done) => {
            test(app)
                .post('/login')
                .send({userId:'TEST1',userPassword:'2222'})
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.LOGIN.PASSWORD_ERROR);
                    done();
                })
        });

    });
};


module.exports = LOGIN_TEST;
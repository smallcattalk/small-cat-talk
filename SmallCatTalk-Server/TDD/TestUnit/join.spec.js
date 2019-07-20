const should = require('should');
const test = require('supertest');
const assert = require('assert');
const app = require('../../app');
const resultCode = require('../../util/resultCode');

const JOIN_TEST = () => {
    describe('@POST /login    로그인 테스트입니다.', () => {

        it('회원가입 성공시 result:1 / user 객체를 반환합니다. ', (done) => {
            test(app)
                .post('/join')
                .send({userId:'TEST1',userName:'작은아빠',userPassword:'1111',profileImgUrl:'profileImgs/default1.png'})
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.JOIN.SUCCESS);
                   // should(res.body).have.property('user');

                    done();
                })

        });
        it('회원가입 성공시 result:1 / user 객체를 반환합니다. ', (done) => {
            test(app)
                .post('/join')
                .send({userId:'TEST2',userName:'아버지',userPassword:'1111',profileImgUrl:'profileImgs/default2.png'})
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.JOIN.SUCCESS);
                    //should(res.body).have.property('user');

                    done();
                })

        });
        it('회원가입 성공시 result:1 / user 객체를 반환합니다. ', (done) => {
            test(app)
                .post('/join')
                .send({userId:'TEST3',userName:'어머니',userPassword:'1111',profileImgUrl:'profileImgs/default3.png'})
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.JOIN.SUCCESS);
                    //should(res.body).have.property('user');

                    done();
                })

        });

        it('회원가입 성공시 result:1 / user 객체를 반환합니다. ', (done) => {
            test(app)
                .post('/join')
                .send({userId:'TEST4',userName:'삼촌',userPassword:'1111',profileImgUrl:'profileImgs/default4.png'})
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.JOIN.SUCCESS);
                    //should(res.body).have.property('user');

                    done();
                })

        });
        it('회원가입 성공시 result:1 / user 객체를 반환합니다. ', (done) => {
            test(app)
                .post('/join')
                .send({userId:'TEST5',userName:'이모',userPassword:'1111',profileImgUrl:'profileImgs/default5.png'})
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.JOIN.SUCCESS);
                    //should(res.body).have.property('user');

                    done();
                })

        });
        it('회원가입 성공시 result:1 / user 객체를 반환합니다. ', (done) => {
            test(app)
                .post('/join')
                .send({userId:'TEST6',userName:'큰삼촌',userPassword:'1111',profileImgUrl:'profileImgs/default6.png'})
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.JOIN.SUCCESS);
                    //should(res.body).have.property('user');

                    done();
                })

        });
        it('회원가입 성공시 result:1 / user 객체를 반환합니다. ', (done) => {
            test(app)
                .post('/join')
                .send({userId:'TEST7',userName:'작은삼촌',userPassword:'1111',profileImgUrl:'profileImgs/default7.png'})
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.JOIN.SUCCESS);
                    //should(res.body).have.property('user');

                    done();
                })

        });
        it('회원가입 성공시 result:1 / user 객체를 반환합니다. ', (done) => {
            test(app)
                .post('/join')
                .send({userId:'TEST8',userName:'사촌동생',userPassword:'1111',profileImgUrl:'profileImgs/default8.png'})
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.JOIN.SUCCESS);
                    //should(res.body).have.property('user');

                    done();
                })

        });
        it('회원가입 성공시 result:1 / user 객체를 반환합니다. ', (done) => {
            test(app)
                .post('/join')
                .send({userId:'TEST9',userName:'할어버지',userPassword:'1111',profileImgUrl:'profileImgs/default9.png'})
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.JOIN.SUCCESS);
                    //should(res.body).have.property('user');

                    done();
                })

        });
        it('회원가입 성공시 result:1 / user 객체를 반환합니다. ', (done) => {
            test(app)
                .post('/join')
                .send({userId:'TEST10',userName:'사촌형',userPassword:'1111',profileImgUrl:'profileImgs/default10.png'})
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.JOIN.SUCCESS);
                    //should(res.body).have.property('user');

                    done();
                })

        });
        it('회원가입 성공시 result:1 / user 객체를 반환합니다. ', (done) => {
            test(app)
                .post('/join')
                .send({userId:'TEST11',userName:'사촌누나',userPassword:'1111',profileImgUrl:'profileImgs/default11.png'})
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.JOIN.SUCCESS);
                    //should(res.body).have.property('user');

                    done();
                })

        });
        it('회원가입 성공시 result:1 / user 객체를 반환합니다. ', (done) => {
            test(app)
                .post('/join')
                .send({userId:'TEST12',userName:'이모할머니',userPassword:'1111',profileImgUrl:"profileImgs/default12.png"})
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.JOIN.SUCCESS);
                    //should(res.body).have.property('user');

                    done();
                })

        });
    });
};


module.exports = JOIN_TEST;
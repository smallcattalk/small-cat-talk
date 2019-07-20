const should = require('should');
const test = require('supertest');
const assert = require('assert');
const app = require('../../app');
const resultCode = require('../../util/resultCode');

const SEARCH_TEST = () => {
    describe('@GET /search/user/:id 유저 검색  테스트 입니다.', () => {

        it('검색된 유저가 존재시 result:1 반환', (done) => {
            test(app)
                .get('/search/user/TEST')
                .expect(200)
                .end((err, res) => {
                    should(res.body).have.property('result', resultCode.SEARCH_USER.SUCCESS);
                    should(res.body).have.property('users');
                    done();
                })

        });

        it('검색된 유저가 없을시 0 반환', (done) => {
            test(app)
                .get('/search/user/NO')
                .expect(404)
                .end((err, res) => {
                  should(res.body).have.property('result', resultCode.SEARCH_USER.USER_NOT_FOUND);
                  done();
                })
        });

    });

};


module.exports = SEARCH_TEST;
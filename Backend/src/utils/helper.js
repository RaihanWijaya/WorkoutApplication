const bcrypt = require('bcryptjs');

async function comparePassword(password, hash) {
    return await bcrypt.compare(password, hash);
}

async function hashPassword(password) {
    return await bcrypt.hash(password, 10);
}

module.exports = {
    hashPassword,
    comparePassword
};
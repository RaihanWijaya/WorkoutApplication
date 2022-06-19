/**
 * Helper is used for passwords functions.
 */

const bcrypt = require('bcryptjs');

//Compare the passed password with the hashed password
async function comparePassword(password, hash) {
    return await bcrypt.compare(password, hash);
}

//Hash the password
async function hashPassword(password) {
    return await bcrypt.hash(password, 10);
}

module.exports = {
    hashPassword,
    comparePassword
};
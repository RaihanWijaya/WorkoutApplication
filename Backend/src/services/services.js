const db = require('../configs/db.config');
const helper = require('../utils/helper');
const workoutController = ('../controllers/controllers');

//Users, login - register - getUser
async function login (workout){
    const {username, password} = workout;
    const query = `SELECT * FROM users WHERE username = '${username}'`;
    const result = await db.query(query);
    if(result.rowCount === 0){
        return {message: 'User not found'};
    }
    const user = result.rows[0];
    if(helper.comparePassword(password, user.password)){
        return user;
    }
    return {message: 'Wrong password'};
}

async function register (workout){
    const {username, password} = workout;
    const hashedPassword = await helper.hashPassword(password);
    const query = `INSERT INTO users (username, password) VALUES ('${username}', '${hashedPassword}')`;
    const result = await db.query(query);
    return {message: 'User created'};
}

async function getUser (user){
    if (user === null){
        return {message: 'User not logged in'};
    }
    else{
        const query = `SELECT * FROM users WHERE userid = '${user.userid}'`;
        const result = await db.query(query);
        return {workout: result.rows};
    }
}

//Tracking, createTracking - getTracking - deleteTracking
async function createTracking (workout, user){
    const {weight, height} = workout;
    if (user === null){
        return {message: 'User not logged in'};
    }
    else{
        const bmi = (height * height) / weight;
        const query = `INSERT INTO tracking (weight, height, bmi, userid) VALUES ('${weight}', '${height}', '${bmi}', '${user.userid}')`;
        const result = await db.query(query);
        return {message: 'Tracking added'};
    }
}

async function getTracking (user){
    if (user === null){
        return {message: 'User not logged in'};
    }
    else{
        const query = `SELECT * FROM tracking WHERE userid = '${user.userid}'`;
        const result = await db.query(query);
        return {workout: result.rows};
    }
}

async function deleteTracking (workout, user){
    const {trackingid} = workout;
    if (user === null){
        return {message: 'User not logged in'};
    }
    else{
        const query = `DELETE FROM tracking WHERE trackingid = '${trackingid}' AND userid = '${user.userid}'`;
        const result = await db.query(query);
        return {message: 'Tracking removed'};
    }
}

//Workout, createWorkout - updateWorkout - getWorkout - deleteWorkout
async function createWorkout (workout, user){
    const {name, bodypart, reps, sets} = workout;
    if (user === null){
        return {message: 'User not logged in'};
    }
    else{
        const queryCreate = `INSERT INTO workout (name, bodypart, reps, sets, userid) VALUES ('${name}', '${bodypart}', '${reps}', '${sets}', '${user.userid}')`;
        const resultCreate = await db.query(queryCreate);
        return {message: 'Workout added'};
    }
}

async function updateWorkout (workout, user){
    const {workoutid, name, bodypart, reps, sets} = workout;
    if (user === null){
        return {message: 'User not logged in'};
    }
    else{
        const query = `UPDATE workout SET name = '${name}', bodypart = '${bodypart}', reps = '${reps}', sets = '${sets}' WHERE workoutid = '${workoutid}' AND userid = '${user.userid}'`;
        const result = await db.query(query);
        return {message: 'Workout updated'};
    }
}

async function getWorkout (user){
    if (user === null){
        return {message: 'User not logged in'};
    }
    else{
        const query = `SELECT * FROM workout WHERE userid = '${user.userid}'`;
        const result = await db.query(query);
        return {workout: result.rows};
    }
}

async function deleteWorkout (workout, user){
    const {workoutid} = workout;
    if (user === null){
        return {message: 'User not logged in'};
    }
    else{
        const query = `DELETE FROM workout WHERE workoutid = '${workoutid}' AND userid = '${user.userid}'`;
        const result = await db.query(query);
        return {message: 'Workout deleted'};
    }
}

module.exports = {
    login,
    register,
    getUser,
    createTracking,
    getTracking,
    deleteTracking,
    createWorkout,
    updateWorkout,
    getWorkout,
    deleteWorkout
}
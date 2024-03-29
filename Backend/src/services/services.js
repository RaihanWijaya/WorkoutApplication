/**
 * Services is used to call the functions in the database.
 */

const db = require('../configs/db.config');
const helper = require('../utils/helper');
const workoutController = ('../controllers/controllers');

//Users, login - register - getUser - deleteUser
async function login (workout){
    const {username, password} = workout;
    if(username !== null || password !== null){
        const query = `SELECT * FROM users WHERE username = '${username}'`;
        const result = await db.query(query);
        if(result.rowCount === 0){
            return {message: 'User not found'};
        }
        const user = result.rows[0];
        if(helper.comparePassword(password, user.password)){
            return {user, message: 'User logged in'};
        }
    }
    return {message: 'Wrong password'};
}

async function register (workout){
    const {username, password} = workout;
    if(username !== null || password !== null){
        //Insert with hashed password
        const hashedPassword = await helper.hashPassword(password);
        const query = `INSERT INTO users (username, password) VALUES ('${username}', '${hashedPassword}')`;
        const result = await db.query(query);
        return {message: 'User created'};
    }
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

async function deleteUser (user){
    if (user === null){
        return {message: 'User not logged in'};
    }
    else{
        //Delete other data in database, not only users
        const query = `DELETE FROM users WHERE userid = '${user.userid}'`;
        const result = await db.query(query);
        const queryTracking = `DELETE FROM tracking WHERE userid = '${user.userid}'`;
        const resultTracking = await db.query(queryTracking);
        const queryWorkout = `DELETE FROM workout WHERE userid = '${user.userid}'`;
        const resultWorkout = await db.query(queryWorkout);
        return {workout: result.rows};
    }
}

//Tracking, createTracking - getTracking - deleteTracking
async function createTracking (workout, user){
    const {weight, height, progress} = workout;
    if (user === null){
        return {message: 'User not logged in'};
    }
    else{
        if(weight !== null || height !== null || progress !== null){
            //BMI = height^2 / weight
            const bmi = (height * height) / weight;
            const query = `INSERT INTO tracking (weight, height, bmi, userid, progress) VALUES ('${weight}', '${height}', '${bmi}', '${user.userid}', '${progress}')`;
            const result = await db.query(query);
            return {message: 'Tracking added'};
        }
    }
}

async function getTracking (user){
    if (user === null){
        return {message: 'User not logged in'};
    }
    else{
        const query = `SELECT * FROM tracking WHERE userid = '${user.userid}' ORDER BY trackingid DESC`;
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
        if (trackingid !== null){
            const query = `DELETE FROM tracking WHERE trackingid = '${trackingid}' AND userid = '${user.userid}'`;
            const result = await db.query(query);
            return {message: 'Tracking removed'};
        }
    }
}

//Workout, createWorkout - updateWorkout - getWorkout - deleteWorkout
async function createWorkout (workout, user){
    const {name, bodypart, reps, sets} = workout;
    if (user === null){
        return {message: 'User not logged in'};
    }
    else{
        if(name !== null || bodypart !== null || reps !== null || sets !== null){
            const queryCreate = `INSERT INTO workout (name, bodypart, reps, sets, userid) VALUES ('${name}', '${bodypart}', '${reps}', '${sets}', '${user.userid}')`;
            const resultCreate = await db.query(queryCreate);
            return {message: 'Workout added'};
        }
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
        if(workoutid !== null){
            const query = `DELETE FROM workout WHERE workoutid = '${workoutid}'`;
            const result = await db.query(query);
            return {message: 'Workout deleted'};
        }
    }
}

module.exports = {
    login,
    register,
    getUser,
    deleteUser,
    createTracking,
    getTracking,
    deleteTracking,
    createWorkout,
    updateWorkout,
    getWorkout,
    deleteWorkout
}
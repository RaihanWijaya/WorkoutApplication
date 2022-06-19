const workoutService = require('../services/services');


async function login(req,res){
    try{
        //const result = await todoService.login(req.body);
        res.json({message: 'authorized'});
    }catch(err){
        res.json(err);
    }
}

async function register(req,res){
    try{
        const result = await workoutService.register(req.body);
        res.json(result);
    }catch(err){
        res.json(err.detail);
    }
}

async function getUser(req,res){
    try{
        const result = await workoutService.getUser(req.user);
        res.json(result);
    }catch(err){
        res.json(err.detail);
    }
}

async function deleteUser(req,res){
    try{
        const result = await workoutService.deleteUser(req.user);
        res.json(result);
    }catch(err){
        res.json(err.detail);
    }
}

async function createTracking(req,res){
    try{
        const result = await workoutService.createTracking(req.body, req.user);
        res.json(result);
    }catch(err){
        res.json(err.detail);
    }
}

async function getTracking(req,res){
    try{
        const result = await workoutService.getTracking(req.user);
        res.json(result);
    }catch(err){
        res.json(err.detail);
    }
}

async function deleteTracking(req,res){
    try{
        const result = await workoutService.deleteTracking(req.body, req.user);
        res.json(result);
    }catch(err){
        res.json(err.detail);
    }
}

async function createWorkout(req,res){
    try{
        const result = await workoutService.createWorkout(req.body, req.user);
        res.json(result);
    }catch(err){
        res.json(err.detail);
    }
}

async function updateWorkout(req,res){
    try{
        const result = await workoutService.updateWorkout(req.body, req.user);
        res.json(result);
    }catch(err){
        res.json(err.detail);
    }
}

async function getWorkout(req,res){
    try{
        const result = await workoutService.getWorkout(req.user);
        res.json(result);
    }catch(err){
        res.json(err.detail);
    }
}

async function deleteWorkout(req,res){
    try{
        const result = await workoutService.deleteWorkout(req.body, req.user);
        res.json(result);
    }catch(err){
        res.json(err.detail);
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
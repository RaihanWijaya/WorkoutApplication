const passport = require('passport');
const express = require('express');
const router = express.Router();
const workoutController = require('../controllers/controllers');

//Table users, login - register
router.get('/login',passport.authenticate('local'), workoutController.login);
router.post('/register', workoutController.register);
router.get('/getUser', workoutController.getUser);

//Table tracking, createTracking - deleteTracking
router.route('/tracking')
    .post(workoutController.createTracking)
    .get(workoutController.getTracking)
    .delete(workoutController.deleteTracking);

//Table workout, createWorkout - deleteWorkout
router.route('/workout')
    .post(workoutController.createWorkout)
    .put(workoutController.updateWorkout)
    .get(workoutController.getWorkout)
    .delete(workoutController.deleteWorkout);

module.exports = router;
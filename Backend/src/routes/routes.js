/**
 * The list of available routes for the application to connect to.
 */

const passport = require('passport');
const express = require('express');
const router = express.Router();
const workoutController = require('../controllers/controllers');

//Table users, login - register - getUser - deleteUser
router.post('/login',passport.authenticate('local'), workoutController.login);
router.post('/register', workoutController.register);
router.get('/getUser', workoutController.getUser);
router.delete('/deleteUser', workoutController.deleteUser);

//Table tracking, createTracking - getTracking - deleteTracking
router.route('/tracking')
    .post(workoutController.createTracking)
    .get(workoutController.getTracking)
router.post('/deleteTracking',workoutController.deleteTracking);

//Table workout, createWorkout - updateWorkout - getWorkout - deleteWorkout
router.route('/workout')
    .post(workoutController.createWorkout)
    .put(workoutController.updateWorkout)
    .get(workoutController.getWorkout)
router.post('/deleteWorkout', workoutController.deleteWorkout);

module.exports = router;
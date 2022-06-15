const {Client} = require('pg');
const db = new Client({
    user: 'mraihanw_sbd',
    host: 'mraihanw-sbd.postgres.database.azure.com',
    database: 'workout_database',
    password: 'Rai12102002',
    port: 5432,
    sslmode: 'require',
    ssl: true
});

db.connect((err) => {
    if (err) {
        console.error('Error connecting to postgres', err);
        return;
    }
    console.log('Connected to postgres');
});

module.exports = db;
const path = require('path');

module.exports = {
    entry: [
        './app/index.js',
        'babel-polyfill'
    ],
    devtool: 'source-map',
    cache: true,
    debug: true,
    resolve: {
        extensions: [
            '',
            '.js',
            '.jsx'
        ]
    },
    output: {
        path: './build',
        filename: 'bundle.js'
    },
    module: {
        loaders: [
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                loaders: [
                    'babel-loader'
                ],
                plugins: ['transform-runtime']
            }
        ]
    }
};
# test-app

Sample figwheel all with cljs tests.

## Setup

To get an interactive development environment run:

    lein figwheel dev

## Testing

Install Karma library for testing locally:

    npm install karma karma-cljs-test --save-dev

Install required launchers:

    npm install karma-chrome-launcher karma-firefox-launcher --save-dev
    npm install karma-safari-launcher karma-opera-launcher --save-dev
    npm install karma-ie-launcher --save-dev

Install cli tool for running Karma unless already installed:

    npm install -g karma-cli

Run tests

    lein doo chrome browser-test
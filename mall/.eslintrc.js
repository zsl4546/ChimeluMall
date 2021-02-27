module.exports = {
  root: true,
  parser: 'vue-eslint-parser',
  env: {
    browser: true,
    node: true,
    es6: true
  },
  extends: [
    'plugin:vue/essential', 'eslint:recommended'
  ],
  plugins: [
    'vue'
  ],

    rules: {
      'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
      'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
      'space-before-function-paren': 0,
      'indent': 0,
      'no-multi-spaces': 'off',
      "no-multiple-empty-lines": [0, { "max": 100 }],
      'arrow-parens': 0,
      'generator-star-spacing': 0,
      "no-unused-vars": [2, {
        "vars": "local",
        "args": "none"
      }],
      "semi": [0],
      "no-mixed-spaces-and-tabs": [0],
      "space-before-blocks": 0,
      "key-spacing": 0,
      "arrow-spacing": 0,
      "no-trailing-spaces": 0,
      "eol-last": 0,
      "prefer-const": 0,
      "quotes": 0,
    },
    parserOptions: {
      parser: 'babel-eslint'
    }

}


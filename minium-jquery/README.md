Minium jQuery (and extensions)
==============================

This library generates `minium-jquery` library that exposes:

* `minium.$`: jQuery library that ensures no conflict occurs with other existing jQuery libraries
* `minium.evalExpression`: used to evaluate a minium expression in the browser
* `minium.loadStyles`: used to load required CSS styles (for instance, for SelectorGadget)

It also generates minified scripts of jQuery extensions required by minium, as well as SelectorGadget scripts.

Generated scripts are then used by `minium-webelements` for javascript injection in sites being tested.

Build
-----

Ensure `node` and `yarn` are both installed and then just run:

```bash
yarn install
```

All generated scripts can now be found under `dist` folder.
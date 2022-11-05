current version: 8.1.0

config file: .minecraft/config/ae2ao.toml


## Cells

1. ### CellFireResistance

   default = false

2. ### CellExplosionResistance

   default = false

3. ### PortableCellFireResistance

   default = false

4. ### PortableCellExplosionResistance

   default = false

5. ### ItemsPerByte

   default = 8

6. ### MbPerByte

   default = 8000

   Milibuckets of fluid per byte.


## Controllers

1. ### ControllerLimits

    default = true 

    Control all controller limits. If is false,
any variant of controller structure is correct.
It overrides other settings.

2. ### ControllerCross

    default = false

    If is false, possible structure with cross
pattern, i.e. two neighbors on two or three axes.

3. ### MultipleControllers

    default = false

    If is true, possible connect to one me net
multiple controller structure.

4. ### ControllerSizeLimits
    
    default = {7, 7, 7}

    Control controller structure size limits.
You have any questions about this?

## Other
1. ### DisableChannels

    default = false

   Disable all channels logic. If true amount 
of used channels is always 0.
forward(100)
fd(100)

backward(100)
bk(100)
back(100)

left(90)
lt(90.0)

right(90)
rt(90.0)

goto(0, 0)
setpos(100, 100)
setposition(0, 0)

sety(100)
setx(100)

setheading(90)
setheading(90)

home()

circle(100, 270, 7)

dot(40, 'red')
dot(20, (0, 0, 1))
forward(50)

stamp_id = stamp()
forward(50)
clearstamp(stamp_id)

speed("slow")

stamp()
forward(10)
stamp()
forward(10)
stamp()
forward(10)
clearstamps()

for _ in range(10):
    undo()




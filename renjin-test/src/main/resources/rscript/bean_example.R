import(com.example.demo.Customer)

bob <- Customer$new(name = "Bob", age = 36)
carol <- Customer$new(name = "Carol", age = 41)
cat("'bob' is an ", typeof(bob), ", bob$name = ", bob$name, "\n", sep = "")
# the original java methods are also available i.e. the following is equivalent
cat("'bob' is an ", typeof(bob), ", bob$getName() = ", bob$getName(), "\n", sep = "")
import(java.util.HashMap)

# create a new instance of the HashMap class:
ageMap <- HashMap$new()

# call methods on the new instance:
ageMap$put("Bob", 33)
ageMap$put("Carol", 41)

print(ageMap$size())

age <- ageMap$get("Carol")
cat("Carol is ", age, " years old.\n", sep = "")

# Java primitives and their boxed types
# are automatically converted to R vectors:
typeof(age)
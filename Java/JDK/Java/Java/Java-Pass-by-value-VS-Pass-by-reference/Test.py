def five():
    num = 5
    print("num in five() = %d" % num)

def main():
    num = 1
    five()
    print("num in main() = %d" % num)

num = 0
print("num after before main() = %d" % num)
main()
print("num after calling main() = %d" % num)

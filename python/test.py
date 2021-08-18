import tkinter as ttk
from tkinter import filedialog

class Reader(ttk.Frame):
    def __init__(self, master=None):
        super().__init__(master)
        self.master = master
        self.grid(column=0, row=0, sticky=(ttk.N, ttk.W, ttk.E, ttk.S))
        self.pack()

        self.title = ttk.Label(self, text="Reader")
        self.title.pack(side="top")

        self.button = ttk.Button(self, text="Browse", command=self.load_file, width=10)
        self.button.pack()

    def load_file(self):
        fname = filedialog.askopenfilename()
        if fname:
            try:
                print("""here it comes: self.settings["template"].set(fname)""")
            except:
                ttk.messagebox.showerror("Open Source File", "Failed to read file\n'%s'" % fname)
            return

class Transmogrify(ttk.Frame):
    def __init__(self, master=None):
        super().__init__(master)
        self.master = master
        self.grid(column=0, row=0, sticky=(ttk.N, ttk.W, ttk.E, ttk.S))
        self.pack()
        self.reader = Reader(master=self)
        self.create_widgets()

    def create_widgets(self):
        self.hi_there = ttk.Button(self)
        self.hi_there["text"] = "Hello World\n(click me)"
        self.hi_there["command"] = self.say_hi
        self.hi_there.pack(side="top")

        self.quit = ttk.Button(self, text="QUIT", fg="red",
                              command=self.master.destroy)
        self.quit.pack(side="bottom")

        self.test = ttk.Label(self, text="Sample text here")
        self.test.pack(side="top")

    def say_hi(self):
        print("hi there, everyone!")

def main():
    root = ttk.Tk()
    transmogrify = Transmogrify(master=root)
    transmogrify.mainloop()

if __name__ == '__main__':
    main()

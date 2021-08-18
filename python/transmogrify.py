from textual.app import App
from textual import events
from textual.widgets import Placeholder
from textual.reactive import Reactive
from textual.views import GridView
from textual.widget import Widget
from textual.widgets import Button, ButtonPressed, Footer
from rich.align import Align
from rich.console import Console, ConsoleOptions, RenderResult, RenderableType
from rich.padding import Padding
from rich.text import Text
from rich.panel import Panel
from rich.pretty import Pretty
from rich import box
from rich.prompt import Prompt
from rich.table import Table
from rich.style import Style
from rich.syntax import Syntax
from rich.text import Text
import os


class Reader(Widget):
    def __init__(self, *args, **kwargs) -> None:
        super().__init__(*args, **kwargs)
        self.regular_style = Style(color="white", bgcolor="black")
        self.select_style = Style(color="black", bgcolor="white")
        self.index = 0
        self.directory = "."
        self.results = []
        self.selected_filename = ""
        self.file_contents = []
        self.is_search = True
        self.is_preview = False

    def render(self) -> RenderableType:
        if self.is_search:
            return Panel(
                self.search(),
                title="Reader",
                border_style="green",
                box=box.HEAVY,
            )
        elif self.is_preview:
            return Panel(
                self.preview(),
                title="Reader",
                border_style="green",
                box=box.HEAVY,
            )
    
    def search(self) -> RenderableType:
        self.results = os.listdir(self.directory)
        table = Table(title="File Selector")
        table.add_column("Type")
        table.add_column("Filename")
        table.add_column("Size")

        for idx, result in enumerate(self.results):
            table.add_row("File", result, "0B", style=self.select_style if idx == self.index else self.regular_style)
        return Align.center(table, vertical="middle")
    
    def preview(self) -> RenderableType:
        return Syntax("".join(self.file_contents), "text", theme="monokai", line_numbers=True)
    
    def load_preview(self):
        with open(self.selected_filename, "r") as selected_file:
            for idx, line in enumerate(selected_file):
                if idx == 10:
                    break
                self.file_contents.append(line)
    
    async def handle_keypress(self, key):
        if key == "up":
            self.index = max(0, self.index - 1)
            self.is_search = True
            self.is_preview = False
            self.refresh()
        elif key == "down":
            self.index = min(len(self.results) - 1, self.index + 1)
            self.is_search = True
            self.is_preview = False
            self.refresh()
        elif key == "enter":
            self.selected_filename = self.results[self.index]
            self.is_search = False
            self.is_preview = True
            self.load_preview()
            self.refresh()
        elif key == "backspace":
            self.is_search = True
            self.is_preview = False
            self.refresh()


class Window(GridView):
    def __init__(self, *args, **kwargs) -> None:
        super().__init__(*args, **kwargs)
        self.reader = Reader()
    
    async def on_mount(self, event: events.Mount) -> None:
        """Event when widget is first mounted (added to a parent view)."""
        self.grid.add_column(fraction=1, name="main_column", min_size=40)
        self.grid.add_row(fraction=1, name="main_row", min_size=2)
        self.grid.add_areas(
            main="main_column,main_row"
        )
        self.grid.place(
            main=self.reader,
        )

    async def handle_keypress(self, key):
        await self.reader.handle_keypress(key)


class Application(App):
    def __init__(self, *args, **kwargs) -> None:
        super().__init__(*args, **kwargs)
        self.window = Window()
        self.footer = Footer()

    async def on_load(self, event):
        await self.bind("q", "quit")
        await self.bind("up", "keypress_up", "up")
        await self.bind("down", "keypress_down", "down")
        await self.bind("enter", "keypress_enter", "enter")

    async def on_mount(self, event: events.Mount) -> None:
        """Mount the main window and footer."""
        await self.view.dock(self.window)
        await self.view.dock(self.footer, edge="bottom")
    
    async def action_keypress_up(self):
        self.log(f"User pressed the up key (action_keypress)")
        await self.window.handle_keypress("up")

    async def action_keypress_down(self):
        self.log(f"User pressed the down key (action_keypress)")
        await self.window.handle_keypress("down")

    async def on_key(self, event):
        """Called when user hits any key."""
        self.log(f"User pressed the \"{event.key}\" key (on_key)")
        if event.key == "up":
            await self.window.handle_keypress("up")
        elif event.key == "down":
            await self.window.handle_keypress("down")
        elif event.key == "ctrl+m": # Enter
            await self.window.handle_keypress("enter")
        elif event.key == "ctrl+h": # Backspace
            await self.window.handle_keypress("backspace")


Application.run(title="transmogrify", log="textual.log")

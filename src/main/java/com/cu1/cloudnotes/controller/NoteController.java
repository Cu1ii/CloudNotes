package com.cu1.cloudnotes.controller;

import com.cu1.cloudnotes.entity.Note;
import com.cu1.cloudnotes.service.NoteService;
import com.cu1.cloudnotes.utils.NoteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/list")
    private List<Note> findAllNotes(@RequestParam int categoryId) {
        return noteService.findAllNotes(categoryId);
    }

    @GetMapping("/{id}")
    private Note findNote(@PathVariable int id) {
        return noteService.findNoteById(id);
    }

    @PostMapping("/add")
    private String addNote(@RequestParam int categoryId,
                           @RequestParam String title,
                           @RequestParam String content) {
        Note note = new Note();
        note.setCreateTime(new Date());
        note.setStatus(0);
        note.setTitle(title);
        note.setContent(content);
        note.setCategoryId(categoryId);
        int rows = noteService.addNote(note);

        return NoteUtil.getJsonString(0,"添加笔记成功!");
    }

    @PostMapping("/delete")
    private  String deleteNote(@RequestParam int id){
        int rows = noteService.deleteNoteById(id);
        return NoteUtil.getJsonString(0,"删除笔记成功!");
    }

    @PostMapping("/updatetitle/{id}")
    private String updateTitle(@PathVariable int id,
                               @RequestParam String title) {
        int rows = noteService.updateTitle(id, title);
        return NoteUtil.getJsonString(0,"更新笔记标题成功!");
    }

    @PostMapping("/updateContent/{id}")
    private String updateContent(@PathVariable int id,
                                 @RequestParam String content){
        int rows = noteService.updateContent(id, content);
        return NoteUtil.getJsonString(0,"更新笔记内容成功!");
    }

}

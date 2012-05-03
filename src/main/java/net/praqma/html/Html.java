/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.html;

/**
 *
 * @author Praqma
 */
public class Html {
    public static class Paragraph {
        public String text;
        
        public Paragraph(Html... siblings) {
            this.text = "<p>";
            for(Html h : siblings) {
                this.text+=h;
            }
            this.text += "</p>"; 
        }
        
        public Paragraph(String text) {
            this.text = "<p>"+text+"</p>";
        }

        @Override
        public String toString() {
           return text;
        }      
    }
    
    public static class Anchor {
        private String href;
        
        public Anchor(String href) {
            this.href = "<a href=\""+href+"\">"+href+"</a>";
        }
        
        public Anchor(String href, String linkAlias) {
            this.href = "<a href=\""+href+"\">"+linkAlias+"</a>";
        }

        @Override
        public String toString() {
            return this.href;
        }    
    }
    
    public static class Break {
        private String text;
        public Break() {
            this.text = "<br/>";
        }

        @Override
        public String toString() {
            return text;
        }
        
        
    }
    
    public static class Tr {
        public Tr(Td... columns) {
            
        }
    }
    
    public static class Td {
        
    }
    
    public static class Th {
        
    }
    
    public static class Table {
        
    }
}

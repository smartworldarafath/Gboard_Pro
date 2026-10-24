const fs = require('fs');
const content = fs.readFileSync('README.md', 'utf8');
const regex = /(?:src|srcset)=["']((?:assets|docs)\/[^"'\s]+)["']/g;
let match;
const missing = [];
let count = 0;
while ((match = regex.exec(content)) !== null) {
  count++;
  if (!fs.existsSync(match[1])) {
    missing.push(match[1]);
  }
}
console.log('Checked ' + count + ' asset references in README.md.');
if (missing.length === 0) {
  console.log('SUCCESS: All referenced assets exist on disk!');
} else {
  console.error('MISSING:', missing);
  process.exit(1);
}

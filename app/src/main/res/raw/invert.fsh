#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


// Created by mmichal


void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
	vec2 uv = fragCoord.xy;
    vec4 color = texture2D(iChannel0, uv);

    color.xyz = vec3(1, 1, 1) - color.xyz;
	fragColor = color;
}

void main() {
	mainImage(gl_FragColor, texCoord);
}

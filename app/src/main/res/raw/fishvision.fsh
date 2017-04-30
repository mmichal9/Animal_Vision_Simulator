#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


// Created by mmichal

vec2 barrelDistortion(vec2 uv)
{
    float distortion = 0.2;
    float r = uv.x*uv.x + uv.y*uv.y;
    uv *= 1.6 + distortion * r + distortion * r * r;
    return uv;
}


void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
	vec2 uv = fragCoord.xy;
    uv = uv * 2.0 - 1.0;
    uv = barrelDistortion(uv);
    uv = 0.5 * (uv * 0.5 + 1.0);
	fragColor = texture2D(iChannel0, uv);
}

void main() {
	mainImage(gl_FragColor, texCoord);
}
